package io.muoncore.protocol.event;

import com.google.gson.annotations.SerializedName;
import io.muoncore.codec.Codecs;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

/**
 * A canonical Event for Muon
 */
@Getter
@ToString
public class Event {

  private String id;
  @SerializedName("event-type")
  private String eventType;
  @SerializedName("stream-name")
  private String streamName;

  private String schema;
  @SerializedName("caused-by-id")
  private String causedById;
  @SerializedName("caused-by-relation")
  private String causedByRelation;

  @SerializedName("service-id")
  private String service;
  @SerializedName("order-id")
  private Long orderId;
  @SerializedName("event-time")
  private Long eventTime;
  private Map payload;
  private transient Codecs codecs;

  public Event(String id, String eventType, String streamName, String schema, String causedById, String causedByRelation, String service, Long orderId, Long eventTime, Map payload, Codecs codecs) {
    this.id = id;
    this.eventType = eventType;
    this.streamName = streamName;
    this.schema = schema;
    this.causedById = causedById;
    this.causedByRelation = causedByRelation;
    this.service = service;
    this.orderId = orderId;
    this.eventTime = eventTime;
    this.payload = payload;
    this.codecs = codecs;
  }

  public void setCodecs(Codecs codecs) {
    this.codecs = codecs;
  }

  public String getEventType() {
    return eventType;
  }

  public String getStreamName() {
    return streamName;
  }

  public String getSchema() {
    return schema;
  }

  public String getCausedById() {
    return causedById;
  }

  public String getCausedByRelation() {
    return causedByRelation;
  }

  public String getService() {
    return service;
  }

  public Long getOrderId() {
    return orderId;
  }

  public Long getEventTime() {
    return eventTime;
  }

  public <X> X getPayload(Class<X> type) {
    if (type.isAssignableFrom(Map.class)) {
      return (X) payload;
    }
    Codecs.EncodingResult result = codecs.encode(payload, new String[]{"application/json"});
    return codecs.decode(result.getPayload(), result.getContentType(), type);
  }
}
