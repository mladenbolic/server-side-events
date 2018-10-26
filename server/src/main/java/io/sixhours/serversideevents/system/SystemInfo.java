package io.sixhours.serversideevents.system;

import lombok.Value;

@Value
public class SystemInfo {

  MemoryUsage memoryUsage;
  CpuUsage cpuUsage;

  @Value
  static class MemoryUsage {
    double initial;
    double heap;
    double maxHeap;
    double committed;
  }

  @Value
  static class CpuUsage {
    double cpuLoad;
  }
}
