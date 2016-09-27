package etl.log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction2;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import etl.cmd.LoadDataCmd;
import etl.cmd.SaveDataCmd;
import etl.engine.EngineUtil;
import scala.Tuple2;

public class StreamLogProcessor {
	
	public static final Logger logger = LogManager.getLogger(StreamLogProcessor.class);
	
	public static void main(String args[]){
		SparkConf conf = new SparkConf().setAppName("mtccore");
		final JavaStreamingContext jsc = new JavaStreamingContext(conf, Durations.seconds(EngineUtil.getInstance().getLogInterval()));
		sparkProcess(jsc);
	}
	
	public static void sparkProcess(final JavaStreamingContext jsc){
		try{
			String wfName = "log";
			String defaultFs = EngineUtil.getInstance().getDefaultFs();
			String logTopicName = EngineUtil.getInstance().getLogTopicName();
			
			Map<String, Object> kafkaParams = new HashMap<String, Object>();
			kafkaParams.put("bootstrap.servers", EngineUtil.getInstance().getBootstrapServers());
			kafkaParams.put("group.id", "logconsumerid");
			kafkaParams.put("key.deserializer", StringDeserializer.class);
			kafkaParams.put("value.deserializer", StringDeserializer.class);
			
			logger.info(String.format("kafkaParams:%s", kafkaParams));
			JavaInputDStream<ConsumerRecord<Object, Object>> ds = KafkaUtils.createDirectStream(jsc, 
					LocationStrategies.PreferConsistent(), 
					ConsumerStrategies.Subscribe(Arrays.asList(new String[]{logTopicName}), kafkaParams));
			
			JavaDStream<Tuple2<String,String>> csvs = ds.map(new Function<ConsumerRecord<Object,Object>,Tuple2<String,String>>(){
				private static final long serialVersionUID = 1L;

				@Override
				public Tuple2<String, String> call(ConsumerRecord<Object, Object> v1) throws Exception {
					return new Tuple2<String, String>(v1.key().toString(), v1.value().toString());
				}
				
			});
			
			csvs.cache().foreachRDD(new VoidFunction2<JavaRDD<Tuple2<String,String>>, Time>(){
				private static final long serialVersionUID = 1L;
				@Override
				public void call(JavaRDD<Tuple2<String,String>> v1, Time v2) throws Exception {
					if (!v1.isEmpty() && v1.count()>0){
						String batchid = v2.toString();
						
						SaveDataCmd saveDataCmd = new SaveDataCmd(wfName, batchid, null, defaultFs, null);
						saveDataCmd.setSendLog(false);
						saveDataCmd.sparkProcessKeyValue(v1);
						
						LoadDataCmd loadDataCmd = new LoadDataCmd(wfName, batchid, null, "log", defaultFs, null);
						loadDataCmd.setSendLog(false);
						EngineUtil.getInstance().processJavaCmd(loadDataCmd);
					}
				}
			});
			
			jsc.start();
			jsc.awaitTermination();
			jsc.close();
		}catch(Exception e){
			logger.error("", e);
		}
	}
}
