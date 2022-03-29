package br.com.ernanilima.jmercadobackend.resource;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class JMercadoResource {

    /**
     * Usado para verificar se o servidor esta funcionando
     * @return ResponseEntity<String>
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> connectionOk() {
        String result = """
                        {
                            "status": 200,
                            "message": "Servidor conectado"
                        }
                      """;
        return ResponseEntity.ok().body(result);
    }
}
