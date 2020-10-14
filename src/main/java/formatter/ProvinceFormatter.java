package formatter;

import model.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import repository.ProvinceRepository;
import service.ProvinceService;

import java.text.ParseException;
import java.util.Locale;


@Component
public class ProvinceFormatter implements Formatter<Province> {
    private ProvinceService provinceService;

    public ProvinceFormatter(){

    }

    @Autowired
    public ProvinceFormatter(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }
    @Override
    public Province parse(String text, Locale locale) throws ParseException {
        return provinceService.findById(Integer.parseInt(text));
    }

    @Override
    public String print(Province object, Locale locale) {
        return "[" + object.getId() + ", " +object.getName() + "]";
    }
}
