public class Connector_ConnectorEE_Elevator {
    private Elevator elevator;
    private Connector_Elevator_ElevatorScheduler connector_Elevator_ElevatorScheduler;

    public Connector_ConnectorEE_Elevator() {
        this.elevator = new Elevator();
        this.connector_Elevator_ElevatorScheduler = new Connector_Elevator_ElevatorScheduler();
    }

    private void selectorMethod(Dataflow dataflow) {
        if() {
            connector_Elevator_ElevatorScheduler.service(dataflow)
        } else {
            elevator.service(dataflow)
        }
    }
}
