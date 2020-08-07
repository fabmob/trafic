package com.datex.batch;

import com.datex.batch.model.*;
import com.datex.batch.steps.*;
import com.datex.batch.tasklet.LogTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public LogTasklet logtasklet;

    @Autowired
    public DataSource dataSource;

    @Bean
    @StepScope
    public Resource getResource(@Value("#{jobParameters['targetFile']}") String targetFile) {
        return new FileSystemResource(targetFile);
    }

    @Bean
    @StepScope
    public ClassPathResource getSourceFile(@Value("#{jobParameters['sourceFile']}") String sourceFile) {
        return new ClassPathResource(sourceFile);
    }

    @Bean
    @StepScope
    public DatexTrafficReader getDatexTrafficReader(@Value("#{jobParameters['targetUrl']}") String targetUrl) {
        return new DatexTrafficReader(targetUrl);
    }


    @Bean
    @StepScope
    public DatexMeteoReader getDatexMeteoReader() {
        return new DatexMeteoReader();
    }

    @Bean
    @StepScope
    public TelraamTrafficReader getTelraamTrafficReader() {
        return new TelraamTrafficReader();
    }

    @Bean
    public DatexTrafficProcessor getTrafficProcessor() {
        return new DatexTrafficProcessor();
    }

    @Bean
    public DatexTrafficCSVProcessor getTrafficCSVProcessor() {
        return new DatexTrafficCSVProcessor();
    }
    @Bean
    public TelraamTrafficProcessor getTelraamTrafficProcessor() {
        return new TelraamTrafficProcessor();
    }

    @Bean
    public DatexMeteoProcessor getMeteoProcessor() {
        return new DatexMeteoProcessor();
    }

    @Bean
    public FlatFileItemWriter<TrafficMeasure> writerCSV() {
        //Create writer instance
        FlatFileItemWriter<TrafficMeasure> writer = new FlatFileItemWriter<>();

        //Set output file location
        writer.setResource(getResource(null));

        //All job repetitions should "append" to same output file
        writer.setAppendAllowed(true);

        //Name field values sequence based on object properties
        writer.setLineAggregator(new DelimitedLineAggregator<TrafficMeasure>() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<TrafficMeasure>() {
                    {
                        setNames(new String[]{"id", "measurementTime", "latitude", "longitude", "direction", "road", "averageVehicleSpeed", "vehicleFlowRate", "trafficConcentration"});
                    }
                });
            }
        });
        return writer;
    }

    @Bean
    public JdbcBatchItemWriter<TrafficPoint> writer() {
        JdbcBatchItemWriter<TrafficPoint> itemWriter = new JdbcBatchItemWriter<>();

        itemWriter.setDataSource(this.dataSource);
//        itemWriter.setSql("INSERT INTO trafic(time , camera , latitude, longitude, direction, road , averageVehicleSpeed, vehicleFlowRate, trafficConcentration)" +
//                " VALUES (:time, :camera, :latitude,:longitude,:direction,:road,:averagevehiclespeed,:vehicleflowrate,:trafficconcentration)" +
//                " ON CONFLICT (time, camera) DO UPDATE " +
//                " SET averageVehicleSpeed = excluded.averageVehicleSpeed, " +
//                " vehicleFlowRate = excluded.vehicleFlowRate, " +
//                " trafficConcentration = excluded.trafficConcentration;");

        itemWriter.setSql("INSERT INTO trafic_time(measure_datetime , camera , average_vehicle_speed, vehicle_flow_rate, traffic_concentration)" +
                " VALUES (:time, :camera, :averagevehiclespeed,:vehicleflowrate,:trafficconcentration)" +
                " ON CONFLICT (measure_datetime, camera) DO UPDATE " +
                " SET average_vehicle_speed = excluded.average_vehicle_speed, " +
                " vehicle_flow_rate = excluded.vehicle_flow_rate, " +
                " traffic_concentration = excluded.traffic_concentration;");

        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
        itemWriter.afterPropertiesSet();

        return itemWriter;
    }

    @Bean
    public JdbcBatchItemWriter<MeteoMeasure> writerMeteo() {
        JdbcBatchItemWriter<MeteoMeasure> itemWriter = new JdbcBatchItemWriter<>();

        itemWriter.setDataSource(this.dataSource);

        itemWriter.setSql("INSERT INTO meteo(measure_datetime , point , humidity , no_precipitation , millimetres_per_hour_intensity , road_surface_temperature , temperature_below_road_surface , weather_related_road_condition_type , air_temperature , dew_point_temperature , wind_speed , maximum_wind_speed , wind_direction_bearing , status_type)\n" +
                "                 VALUES (:time, :id, :humidity, :noPrecipitation, :millimetresPerHourIntensity, :roadSurfaceTemperature, :temperatureBelowRoadSurface, :weatherRelatedRoadConditionType, :airTemperature, :dewPointTemperature, :windSpeed, :maximumWindSpeed, :windDirectionBearing, :statusType)\n" +
                "                 ON CONFLICT (measure_datetime, point) DO UPDATE SET millimetres_per_hour_intensity = excluded.millimetres_per_hour_intensity");

        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
        itemWriter.afterPropertiesSet();
        return itemWriter;
    }

    @Bean
    public JdbcBatchItemWriter<SegmentPoint> writerTelraam() {
        JdbcBatchItemWriter<SegmentPoint> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(this.dataSource);
        itemWriter.setSql(Datex.INSERT_TELRAAM);
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
        itemWriter.afterPropertiesSet();
        return itemWriter;
    }

    @Bean
    public Step stepTelraam(JdbcBatchItemWriter<SegmentPoint> writerTelraam) {
        return stepBuilderFactory.get("stepTelraam")
                .<SegmentMeasure, SegmentPoint>chunk(10)
                .reader(getTelraamTrafficReader())
                .processor(getTelraamTrafficProcessor())
                .writer(writerTelraam)
                .build();
    }

    @Bean
    public Step stepMeteo(JdbcBatchItemWriter<MeteoMeasure> writerMeteo) {
        return stepBuilderFactory.get("stepM")
                .<MeteoMeasure, MeteoMeasure>chunk(10)
                .reader(getDatexMeteoReader())
                .processor(getMeteoProcessor())
                .writer(writerMeteo)
                .build();
    }

    @Bean
    public Step step(JdbcBatchItemWriter<TrafficPoint> writer) {
        return stepBuilderFactory.get("stepD")
                .<TrafficMeasure, TrafficPoint>chunk(10)
                .reader(getDatexTrafficReader(null))
                .processor(getTrafficProcessor())
                .writer(writer)
                .build();
    }

    @Bean(name = "readDatexJob")
    public Job readDatexJob(Step step) {
        return jobBuilderFactory.get("readDatexJob")
                .incrementer(new RunIdIncrementer())
                .flow(step)
                .end()
                .build();
    }

    @Bean(name = "logTaskletJob")
    public Job logTaskletJob() {
        return this.jobBuilderFactory.get("logTasklet").incrementer(new RunIdIncrementer())
                .start(stepBuilderFactory.get("stepTask").tasklet(logtasklet).build()).build();
    }

    @Bean(name = "readMeteoJob")
    public Job readMeteoJob(Step stepMeteo) {
        return jobBuilderFactory.get("readMeteoJob")
                .incrementer(new RunIdIncrementer())
                .flow(stepMeteo)
                .end()
                .build();
    }

    @Bean(name = "readTelraamJob")
    public Job readTelraamJob(Step stepTelraam) {
        return jobBuilderFactory.get("readTelraamJob")
                .incrementer(new RunIdIncrementer())
                .flow(stepTelraam)
                .end()
                .build();
    }


    @Bean
    public FlatFileItemReader<TrafficMeasureCSV> readerTrafficMeasureCSV() {
        return new FlatFileItemReaderBuilder<TrafficMeasureCSV>()
                .name("trafficMeasureCSVItemReader")
                //.resource(new ClassPathResource(getSourceFile(null)))
                .resource(getSourceFile(null))
                .delimited()
                .delimiter(";")
                .names(new String[]{"id", "measurementTime","latitude","longitude","direction","road","status","averageVehicleSpeed","vehicleFlowRate","trafficConcentration"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<TrafficMeasureCSV>() {{
                    setTargetType(TrafficMeasureCSV.class);
                }})
                .build();
    }

    @Bean
    public Step stepCSV(JdbcBatchItemWriter<TrafficPoint> writer,FlatFileItemReader<TrafficMeasureCSV> readerTrafficMeasureCSV) {
        return stepBuilderFactory.get("stepCSV")
                .<TrafficMeasureCSV, TrafficPoint>chunk(10)
                .reader(readerTrafficMeasureCSV)
                .processor(getTrafficCSVProcessor())
                .writer(writer)
                .faultTolerant()
                .skipLimit(1000)
                .skip(ParseException.class)
                .build();
    }

    @Bean(name = "readTrafficCSVJob")
    public Job readTrafficCSVJob(Step stepCSV) {
        return jobBuilderFactory.get("readTrafficCSVJob")
                .incrementer(new RunIdIncrementer())
                .flow(stepCSV)
                .end()
                .build();
    }

}
