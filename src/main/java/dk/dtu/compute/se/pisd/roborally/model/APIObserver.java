package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

public class APIObserver extends Subject {

    public void notifyAPI()
    {
        notifyChange();
    }
}
