package io.sixhours.serversideevents.system;

import java.time.Duration;
import java.time.LocalTime;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
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

  @GetMapping("/stream-sse")
  public Flux<ServerSentEvent<String>> streamEvents() {
    return Flux.interval(Duration.ofSeconds(1))
        .map(sequence -> ServerSentEvent.<String> builder()
            .id(String.valueOf(sequence))
            .event("periodic-event")
            .data("SSE - " + LocalTime.now().toString())
            .build());
  }

  // https://stackoverflow.com/questions/37985719/spring-boot-actuator-to-give-cpu-usage
}
