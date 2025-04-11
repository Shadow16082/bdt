







import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
public class WordCount
{
  public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable>
  {
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException 
    {
      StringTokenizer itr = new StringTokenizer(value.toString());
      while (itr.hasMoreTokens()) 
      {
        word.set(itr.nextToken());
        context.write(word, one);
      }
    }
  }
 public static class IntSumReducer extends Reducer<Text,IntWritable,Text,IntWritable> 
  {
    private IntWritable result = new IntWritable();
    public void reduce(Text key, Iterable<IntWritable> values, Context context) trows IOException, InterruptedException 
    {
      int sum = 0;
     for (IntWritable val : values) 
      {
        sum += val.get();
      }
      result.set(sum);
      context.write(key, result);
    }
  }
  public static void main(String[] args) throws Exception 
  {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "word count");
    job.setJarByClass(WordCount.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}

 1. Open cloudera quickstart vm
 2. open Eclipse application  in cloudera
 3. select file-->new-->java project
 4. Give project name , here we gave WordCount3,  Click on Next
 5. Select Libraries tab and select add external jars
 6. Select the path :Usr/lib/hadoop
 7. Click on Client folder and Select all jar files, Click ok
 8. Click Finish
 9. WordCount (Rightclick) --> File -->New --> Class
 10. Give name as per your filename, here I gave as WordCount, Finish
11. Type or paste java program for wordcount (WordCount.java), Save
12. Rightclick on WordCount3 --> Export -->Java -->Jar file (wordcount3.jar), Finish
13. open Terminal and enter the following
14. cat > /home/cloudera/inputfile2.txt and type in the lines of text
15. hdfs dfs -mkdir /myinputfolder 
16. hdfs dfs -put /home/cloudera/inputfile2.txt/myinputfolder/
17. hdfs dfs -cat /myinputfolder/inputfile2.txt
    This command is used to display the contents of the file inputfile2.txt
18. hadoop jar /home/cloudera/wordcount3.jar WordCount /myinputfolder/inputfile2.txt /outputfolder
    filename ie., .java file, here I gave WordCount.java
    Wait for the execution to be done by the system
19. hdfs dfs -cat /outputfolder/part-r-00000
    displays the frequency of number of words occured in the file  (inputfile2.txt)
    highlighted text is the contents of the file (inputfile2.txt)
    currently highlighted text is the output of the wordcount program
                  ---------------------END-------------------------
