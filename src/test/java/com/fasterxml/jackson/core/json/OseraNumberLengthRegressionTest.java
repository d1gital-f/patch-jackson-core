package com.fasterxml.jackson.core.json;

import java.math.BigInteger;

import com.fasterxml.jackson.core.*;

/**
 * OSERA playground regression test (7 Sept 2026). A long number literal is parsed exactly.
 * Placeholder for the backport of upstream FasterXML/jackson-core#943 (CVE-2025-52999): the
 * playground exercises the release flow and the source side fitness checks, not the fix itself.
 */
public class OseraNumberLengthRegressionTest extends BaseTest
{
    public void testLongNumberIsParsedExactly() throws Exception
    {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < 100; ++i) sb.append('7');
        sb.append(']');
        String digits = sb.substring(1, 101);
        JsonFactory f = new JsonFactory();
        try (JsonParser p = f.createParser(sb.toString())) {
            assertToken(JsonToken.START_ARRAY, p.nextToken());
            assertToken(JsonToken.VALUE_NUMBER_INT, p.nextToken());
            assertEquals(new BigInteger(digits), p.getBigIntegerValue());
            assertToken(JsonToken.END_ARRAY, p.nextToken());
        }
    }
}
