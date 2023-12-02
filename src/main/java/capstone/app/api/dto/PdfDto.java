package capstone.app.api.dto;

import capstone.app.domain.Deal;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PdfDto {
    private String name;
    private Long id;

    static public PdfDto dealToPdfDto(Deal deal){
        return new PdfDto(deal.getPdf(), deal.getId());
    }
}
