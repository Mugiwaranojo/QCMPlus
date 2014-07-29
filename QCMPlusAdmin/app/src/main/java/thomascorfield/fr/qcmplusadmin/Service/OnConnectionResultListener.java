package thomascorfield.fr.qcmplusadmin.Service;

public interface OnConnectionResultListener {

    void onConnectionSuccess();

    void onConnectionError(Exception e);
}
