package tech.tablesaw.columns.booleans;

import com.google.common.collect.Lists;
import tech.tablesaw.api.ColumnType;
import tech.tablesaw.columns.AbstractParser;
import tech.tablesaw.io.csv.CsvReadOptions;

import java.util.Arrays;
import java.util.List;

import static tech.tablesaw.api.BooleanColumn.*;

public class BooleanParser extends AbstractParser<Boolean> {

    // A more restricted set of 'false' strings that is used for column type detection
    private static final List<String> FALSE_STRINGS_FOR_DETECTION =
            Arrays.asList("F", "f", "N", "n", "FALSE", "false", "False");

    // A more restricted set of 'true' strings that is used for column type detection
    private static final List<String> TRUE_STRINGS_FOR_DETECTION =
            Arrays.asList("T", "t", "Y", "y", "TRUE", "true", "True");

    // These Strings will convert to true booleans
    private static final List<String> TRUE_STRINGS =
            Arrays.asList("T", "t", "Y", "y", "TRUE", "true", "True", "1");

    // These Strings will convert to false booleans
    private static final List<String> FALSE_STRINGS =
            Arrays.asList("F", "f", "N", "n", "FALSE", "false", "False", "0");

    public BooleanParser(ColumnType columnType) {
        super(columnType);
    }

    public BooleanParser(BooleanColumnType booleanColumnType, CsvReadOptions readOptions) {
        super(booleanColumnType);
        if (readOptions.missingValueIndicator() != null) {
            missingValueStrings = Lists.newArrayList(readOptions.missingValueIndicator());
        }
    }

    @Override
    public boolean canParse(String s) {
        if (isMissing(s)) {
            return true;
        }
        return TRUE_STRINGS_FOR_DETECTION.contains(s)
                || FALSE_STRINGS_FOR_DETECTION.contains(s);
    }

    @Override
    public Boolean parse(String s) {
        if (isMissing(s)) {
            return null;
        } else if (TRUE_STRINGS.contains(s)) {
            return true;
        } else if (FALSE_STRINGS.contains(s)) {
            return false;
        } else {
            throw new IllegalArgumentException("Attempting to convert non-boolean value " + s + " to Boolean");
        }
    }

    @Override
    public byte parseByte(String s) {
        if (isMissing(s)) {
            return MISSING_VALUE;
        } else if (TRUE_STRINGS.contains(s)) {
            return BYTE_TRUE;
        } else if (FALSE_STRINGS.contains(s)) {
            return BYTE_FALSE;
        } else {
            throw new IllegalArgumentException("Attempting to convert non-boolean value " + s + " to Boolean");
        }
    }
}

