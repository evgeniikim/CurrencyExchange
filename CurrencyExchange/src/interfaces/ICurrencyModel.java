package interfaces;

import java.io.Serializable;

public interface ICurrencyModel extends Serializable {

    String getCurrencyCode();
    String getName();

    String getCode();
}