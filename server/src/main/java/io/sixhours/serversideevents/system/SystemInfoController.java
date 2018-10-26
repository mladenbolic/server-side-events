package io.sixhours.serversideevents.system;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/system-info")
@RequiredArgsConstructor
public class SystemInfoController {

  @NonNull
  private SystemInfoService systemInfoService;

  // @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<SystemInfo> getStatus(){
    return systemInfoService.getInfo();
  }
}
