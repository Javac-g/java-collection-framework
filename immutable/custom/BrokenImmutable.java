import java.util.*;

public class BrokenImmutable {
    private final Map<String, List<String>> data;

    public BrokenImmutable(Map<String, List<String>> input) {
        this.data = Collections.unmodifiableMap(input);
    }

    public Map<String, List<String>> getData() {
        return data;
    }

    public static void main(String[] args) {
        Map<String, List<String>> base = new HashMap<>();
        base.put("key", new ArrayList<>(List.of("a")));

        BrokenImmutable bi = new BrokenImmutable(base);
        Map<String, List<String>> d = bi.getData();

        d.get("key").add("🚨");

        System.out.println(bi.getData());
    }
}
