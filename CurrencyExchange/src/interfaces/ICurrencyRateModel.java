package interfaces;

import java.util.Date;

public interface ICurrencyRateModel {
    String getCurrencyCode();
    double getRate();
    Date getDate();
}