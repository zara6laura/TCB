package mx.gob.babien.tcb.dto.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerfilDto {
    private Map<String, Object> values = new LinkedHashMap<>();

    @JsonAnySetter
    public void put(String key, Object value) {
        values.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> asMap() {
        return values;
    }
}

