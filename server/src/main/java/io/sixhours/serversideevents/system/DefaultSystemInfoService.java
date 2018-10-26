package io.sixhours.serversideevents.system;

import io.sixhours.serversideevents.system.SystemInfo.CpuUsage;
import io.sixhours.serversideevents.system.SystemInfo.MemoryUsage;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.time.Duration;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@Service
public class DefaultSystemInfoService implements SystemInfoService {

  @Override
  public Flux<SystemInfo> getInfo() {
    Flux<Long> interval = Flux.interval(Duration.ofSeconds(5));
    Flux<SystemInfo> stockTransactionFlux = Flux.fromStream(Stream.generate(() -> new SystemInfo(getMemoryUsage(), getCpuUsage())));
    
    return Flux.zip(interval, stockTransactionFlux).map(Tuple2::getT2);
  }

  private MemoryUsage getMemoryUsage() {
    MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
    double initial = (double) memoryMXBean.getHeapMemoryUsage().getInit() / 1073741824;
    double heap = (double) memoryMXBean.getHeapMemoryUsage().getUsed() / 1073741824;
    double maxHeap = (double) memoryMXBean.getHeapMemoryUsage().getMax() / 1073741824;
    double committed = (double) memoryMXBean.getHeapMemoryUsage().getCommitted() / 1073741824;
    MemoryUsage memoryUsage = new MemoryUsage(initial, heap, maxHeap, committed);

    System.out.println(
        String.format("Initial memory: %.2f GB", (double) memoryMXBean.getHeapMemoryUsage().getInit() / 1073741824));
    System.out.println(
        String.format("Used heap memory: %.2f GB", (double) memoryMXBean.getHeapMemoryUsage().getUsed() / 1073741824));
    System.out.println(
        String.format("Max heap memory: %.2f GB", (double) memoryMXBean.getHeapMemoryUsage().getMax() / 1073741824));
    System.out.println(String
        .format("Committed memory: %.2f GB", (double) memoryMXBean.getHeapMemoryUsage().getCommitted() / 1073741824));

    return memoryUsage;
  }

  private CpuUsage getCpuUsage(){
//    ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
//
//    for(Long threadID : threadMXBean.getAllThreadIds()) {
//      ThreadInfo info = threadMXBean.getThreadInfo(threadID);
//      System.out.println("Thread name: " + info.getThreadName());
//      System.out.println("Thread State: " + info.getThreadState());
//      System.out.println(String.format("CPU time: %s ns", threadMXBean.getThreadCpuTime(threadID)));
//    }

//    long elapsedProcessCpuTime = peOperatingSystemMXBean.getProcessCpuTime() - previousJvmProcessCpuTime;
//    // elapsed uptime is in milliseconds
//    long elapsedJvmUptime = runtimeMXBean.getUptime() - previousJvmUptime;
//
//    // total jvm uptime on all the available processors
//    long totalElapsedJvmUptime = elapsedJvmUptime * operatingSystemMXBean.getAvailableProcessors();
//
//    // calculate cpu usage as a percentage value
//    // to convert nanoseconds to milliseconds divide it by 1000000 and to get a percentage multiply it by 100
//    float cpuUsage = elapsedProcessCpuTime / (totalElapsedJvmUptime * 10000F);
//
//    // set old timestamp values
//    previousJvmProcessCpuTime = peOperatingSystemMXBean.getProcessCpuTime();
//    previousJvmUptime = runtimeMXBean.getUptime();
//
//    return cpuUsage

    OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
    return new CpuUsage(osBean.getSystemLoadAverage()*100);
    // monitorInfo.setCpuUtilization(osBean.processCpuLoad*100);
  }
}
