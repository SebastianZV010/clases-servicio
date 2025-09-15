package co.analisys.gimnasio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

    @Schema(description = "Tipo de error (ejemplo: Unauthorized, Forbidden, Bad Request)")
    private String error;

    @Schema(description = "Mensaje descriptivo del error")
    private String message;

    @Schema(description = "Ruta del endpoint donde ocurrió el error")
    private String path;
}
