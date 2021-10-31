import java.util.List;

public interface IConnection {
	List<EventLogs> findEventLogs(int currentPage, int numOfRecords,String eventId,String logEvent);
    long getNumberOfRows(String eventId,String logEvent);
}
