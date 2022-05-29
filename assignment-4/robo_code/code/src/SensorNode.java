public class SensorNode {
    String sensorType = "";

    enum sensor{
        fuelLeft,
        OpptLR,
        OppFb,
        numBarrel,
        barrelLR,
        barrelFB,
        wallDist
    }

    SensorNode(String sensor){
        this.sensorType = sensor;
    }
    SensorNode(){}

    public int evaluate(Robot robot) {
        //String action = this.sensorType;
        sensor action = sensor.valueOf(sensorType);
        switch(action){
            case fuelLeft:
                return robot.getFuel();
            case OpptLR:
                return robot.getOpponentLR();
            case OppFb:
                return robot.getOpponentFB();
            case numBarrel:
                return robot.numBarrels();
            case barrelLR:
                return robot.getClosestBarrelLR();
            case barrelFB:
                return robot.getClosestBarrelFB();
            case wallDist:
                return robot.getDistanceToWall();
        }
        return 0;
    }
    /*
    public boolean evaluate(Robot robot) {
        //String action = this.sensorType;
        sensor action = sensor.valueOf(sensorType);
        switch(action){
            case fuelLeft:
                return true;
            case OpptLR:
                return true;
            case OppFb:
                return true;
            case numBarrel:
                return true;
            case barrelLR:
                return true;
            case barrelFB:
                return true;
            case wallDist:
                return true;
        }

        return false;
    }
    */
    public String toString(){
        return sensorType;
    }
}
