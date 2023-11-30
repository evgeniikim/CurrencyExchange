package interfaces;

import java.io.Serializable;
import java.util.Date;

public interface ICurrencyRateModel extends Serializable {
    String getCurrencyCode();
    double getRate();
    Date getDate();
}