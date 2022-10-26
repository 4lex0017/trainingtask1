package org.example.data;

import java.util.List;

public interface FundDataReader {
    List<FundData> read(String file);

    boolean readsFile(String file);
}
