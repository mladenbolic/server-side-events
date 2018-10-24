package io.sixhours.serversideevents.system;

import reactor.core.publisher.Flux;

public interface SystemInfoService {

  Flux<SystemInfo> getInfo();
}
