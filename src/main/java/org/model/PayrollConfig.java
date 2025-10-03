package org.model;

import java.math.BigDecimal;

public final class PayrollConfig {

    private static final PayrollConfig INSTANCE = new PayrollConfig();

    public final BigDecimal AFP_PERCENT = BigDecimal.valueOf(0.11); // 11%
    public final BigDecimal ONP_PERCENT = BigDecimal.valueOf(0.06); // 6%
    public final BigDecimal SALUD_PERCENT = BigDecimal.valueOf(0.03); // 3%

    private PayrollConfig() {}

    public static PayrollConfig getInstance() {
        return INSTANCE;
    }

}
