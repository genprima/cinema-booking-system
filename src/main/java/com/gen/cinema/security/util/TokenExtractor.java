package com.gen.cinema.security.util;

import com.gen.cinema.security.authentication.Token;

public interface TokenExtractor {
    Token extract(String payload);
} 