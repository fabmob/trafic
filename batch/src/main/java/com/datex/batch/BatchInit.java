package com.datex.batch;

// for init purposes, this is a one shot
public class BatchInit{}

//@SpringBootApplication
//public class BatchInit implements CommandLineRunner {
//    private static final Logger log = LoggerFactory.getLogger(BatchInit.class);
//
//    @Autowired
//    JobLauncher jobLauncher;
//
//    @Autowired
//    @Qualifier("readTrafficCSVJob")
//    Job jobCSV;
//
//    public static void main(String[] args) {
//        SpringApplication.run(BatchInit.class, args);
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        JobParameters params = null;
//        for (String url : Datex.RESOURCE_MAP.keySet()) {
//            String tmsp = String.valueOf(System.currentTimeMillis());
//            log.info(Datex.RESOURCE_MAP.get(url) + " Running ... ");
//            params = new JobParametersBuilder()
//                    .addString("JobID", tmsp)
//                    .addString("sourceFile", Datex.RESOURCE_MAP.get(url))
//                    .toJobParameters();
//            jobLauncher.run(jobCSV, params);
//        }
//    }
//}
