package test.java.com.example.currencyrateprovider.pact;

import com.example.currencyrateprovider.domain.CurrencyPair;
import com.example.currencyrateprovider.domain.CurrencyRate;
import com.example.currencyrateprovider.service.CurrencyRateProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
class PactHttpController {

    private final CurrencyRateProvider rateProvider;

    PactHttpController(CurrencyRateProvider rateProvider) {
        this.rateProvider = rateProvider;
    }

    @GetMapping("/api/rates")
    ResponseEntity<?> getRate(
        @RequestParam("from") String from,
        @RequestParam("to") String to) {
        try {
            CurrencyPair pair = new CurrencyPair(from, to);
            CurrencyRate rate = rateProvider.getRate(pair);

            return ResponseEntity.ok(Map.of(
                "fromCurrency", rate.pair().fromCurrency(),
                "toCurrency", rate.pair().toCurrency(),
                "rate", rate.rate(),
                "timestamp", rate.timestamp().toString()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
}