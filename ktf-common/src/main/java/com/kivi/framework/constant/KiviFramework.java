package com.kivi.framework.constant;

public interface KiviFramework {
    /**
     * KTF框架属性文件
     */
    public static final String FRAMWORK_PLACEHOLDER = "${ktf.config-file}";

    /**
     * dubbo属性文件
     */
    public static final String DUBBO_PLACEHOLDER    = "${ktf.dubbo-file}";

    /** redisson 配置文件 */
    public static final String REDISSON_CONFIG      = "${ktf.redisson.config-file}";

    public interface BasePackages {
        public static final String KTF_COMPONENT_SCAN = "com.kivi";
        /** Component基础扫描包 */
        public static final String COMPONENT_SCAN     = "${ktf.common.component-scan}";

    }

    public interface MapperScan {
        public static final String KTF_MAPPER_SCAN = "com.kivi.**.persist";
    }
}
