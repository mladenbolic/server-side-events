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

    System.out.println();

    return memoryUsage;
  }

  private CpuUsage getCpuUsage(){
    OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
    return new CpuUsage(osBean.getSystemLoadAverage()*100);
  }
}
