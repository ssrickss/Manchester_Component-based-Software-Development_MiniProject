public class Connector_ConnectorEE_InputHandler {
    private InputHandler inputHandler;
    private Connector_Elevator_ElevatorScheduler connector_Elevator_ElevatorScheduler;

    public Connector_ConnectorEE_InputHandler() {
        this.inputHandler = new InputHandler();
        this.connector_Elevator_ElevatorScheduler = new Connector_Elevator_ElevatorScheduler();
    }

    private void sequencerMethod(Dataflow dataflow) {
        inputHandler.receiveRequest(dataflow);
        dataflow = inputHandler.drawRequest();
        connector_Elevator_ElevatorScheduler.receiveRequest(dataflow);
        
    }
}
