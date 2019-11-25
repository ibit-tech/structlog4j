package tech.ibit.structlog4j.extend.formater;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import tech.ibit.structlog4j.Formatter;

import java.util.HashMap;
import java.util.Map;

/**
 * Yaml格式化
 *
 * @author IBIT-TECH
 */
public class YamlFormatter implements Formatter {

    private static final ThreadLocal<Yaml> YAML = ThreadLocal.withInitial(() -> {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);

        return new Yaml(options);
    });

    private static final YamlFormatter INSTANCE = new YamlFormatter();

    public static YamlFormatter getInstance() {
        return INSTANCE;
    }


    @Override
    public String format(Map<String, ?> kvMap) {
        if (null == kvMap || kvMap.isEmpty()) {
            return formatString(YAML.get().dump(kvMap).trim());
        }
        Map<String, Object> newKvMap = new HashMap<>();
        kvMap.forEach((k, v) -> {
            if (v instanceof String) {
                newKvMap.put(k, formatString((String) v));
            } else {
                newKvMap.put(k, v);
            }
        });
        return formatString(YAML.get().dump(newKvMap).trim());
    }
}
