package fr.cogigroup.ingbank.helpers;
import fr.cogigroup.ingbank.models.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AppHelper {

    public AppHelper() {
    }

    public final Map<String, Object> response = new HashMap<>();

    private final Map<Integer, String> messages = new HashMap<Integer, String>() {{
        put(200, "Données disponibles");
        put(201, "Données enregistrées avec succés");
        put(202, "Données mise à jour avec succés");
        put(204, "Données supprimées avec succés");

    }};
    public Map<String, Object> paginate(Object data, Page pageTuts) {
        response.put("content", data);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalItems", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());
        return response;
    }

    public Map<String, Object> response(Object data, Object code, Boolean error, String message) {
        response.put("data", data);
        response.put("status", code == null ? 201 : code);
        response.put("error", error);
        response.put("message", message);
        if (response.containsKey("errors")) {
            response.remove("errors");
        }
        return response;
    }
    public String messageByCode(int code) {
        String message = messages.get(code);
        if (message != null) {
            return message;
        }
        return null;
    }


}
