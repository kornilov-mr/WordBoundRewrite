package main.wordboundrewrite.utils.animations;

import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.util.Pair;

public class ResizeAnimation extends Transition {
    private ImageView imageView;
    private double startHeight;
    private double endWidth;
    private double endHeigth;
    private double startWidth;
    public ResizeAnimation(Duration duration, ImageView image, Pair<Double, Double> startSize, Pair<Double, Double> endSize ) {
        setCycleDuration(duration);

        this.imageView=image;
        this.startHeight = startSize.getValue();
        this.endHeigth =endSize.getValue();
        this.startWidth = startSize.getKey();
        this.endWidth =endSize.getKey();
    }
    public void ChangeToSmall(){
        if(startHeight<endHeigth){
            double temp=this.startHeight;
            this.startHeight=this.endHeigth;
            this.endHeigth=temp;
            temp=this.startWidth;
            this.startWidth=this.endWidth;
            this.endWidth=temp;
        }
    }
    public void ChangeToBig(){
        if(startHeight>endHeigth){
            double temp=this.startHeight;
            this.startHeight=this.endHeigth;
            this.endHeigth=temp;
            temp=this.startWidth;
            this.startWidth=this.endWidth;
            this.endWidth=temp;
        }
    }
    @Override
    public void interpolate(double fraction) {
        imageView.setFitHeight((startHeight*(1-fraction)) + (endHeigth * fraction));
        imageView.setFitWidth((startWidth*(1-fraction)) + (endWidth * fraction));
    }
}

