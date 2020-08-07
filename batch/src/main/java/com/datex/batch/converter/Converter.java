package com.datex.batch.converter;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Converter<I, O> {

    private final Function<I, O> fromIn;

    public Converter(final Function<I, O> fromIn) {
        this.fromIn = fromIn;
    }

    public final O convertFromIn(final I dto) {
        return fromIn.apply(dto);
    }

    public final List<O> createFromDtos(final Collection<I> dtos) {
        return dtos.stream().map(this::convertFromIn).collect(Collectors.toList());
    }

}
