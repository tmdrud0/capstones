package capstone.app.service;

import capstone.app.api.dto.PdfDto;
import capstone.app.domain.Deal;
import capstone.app.domain.Measurement;
import capstone.app.repository.DealRepository;
import capstone.app.repository.PdfRepository;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PdfService {

    private final DealService dealService;
    private final PdfRepository pdfRepository;
    private final UserService userService;

    public FileSystemResource getRecentPdf() {
        String pdfName = dealService.getRecent().getPdf();
        return pdfRepository.find(getPath() + pdfName);
    }

    public FileSystemResource getById(Long id) {
        Deal deal = dealService.getById(id);
        String pdfName =deal.getPdf();
        return pdfRepository.find(getPath() + pdfName);
    }

    public List<PdfDto> getAllOfMine(){
        List<Deal> deals = dealService.getMyDeals();
        return deals.stream().map(d -> PdfDto.dealToPdfDto(d))
                .collect(Collectors.toList());
    }

    private String getPath(){
        return "resources/pdfs/" + userService.getMyUserWithAuthorities().get().getUsername()+"/";
    }

}