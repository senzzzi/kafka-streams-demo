package com.senzzzp.kafkastreamsdemo.streams;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.Stores;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class DemoStreams {

    private final Serde<String> stringSerde = Serdes.String();

    @Bean
    public KStream<String, String> demoKStream(StreamsBuilder streamBuilder) {

        KStream<String, String> kStream = streamBuilder.stream("demo-topic", Consumed.with(stringSerde, stringSerde))
                .groupByKey()
                .aggregate(
                        () -> "initial_value",
                        (a,b,c) -> "old_value",
                        Materialized.<String, String>as(Stores.persistentKeyValueStore("agg")).withKeySerde(stringSerde).withValueSerde(stringSerde).withCachingDisabled())
                .toStream();

        kStream.peek((k, v) -> log.info(k + " " + v))
                .to("demo-topic-2", Produced.with(stringSerde, stringSerde));

        return kStream;
    }
}
