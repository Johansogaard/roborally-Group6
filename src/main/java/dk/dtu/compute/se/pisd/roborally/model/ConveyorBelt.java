package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;

public interface ConveyorBelt extends FieldAction  {
    Heading getHeading();
     void setHeading(Heading heading);
}
