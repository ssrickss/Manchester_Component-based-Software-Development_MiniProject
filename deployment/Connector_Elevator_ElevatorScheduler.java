public class Connector_Elevator_ElevatorScheduler {
    private Elevator elevator;
    private ElevatorScheduler elevatorScheduler;

    public Connector_Elevator_ElevatorScheduler() {
        this.elevator = new Elevator();
        this.elevatorScheduler = new ElevatorScheduler();
    }

    private void sequencerMethod(Dataflow dataflow) {
        elevator.receiveRequest(dataflow);
        dataflow = elevator.drawRequest();
        elevatorScheduler.receiveRequest(dataflow);
        
    }
}
