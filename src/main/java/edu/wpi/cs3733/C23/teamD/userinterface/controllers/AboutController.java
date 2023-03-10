package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;

public class AboutController {

  private int state;
  // pictures of each member of the team
  @FXML ImageView imageOne;
  @FXML ImageView imageTwo;
  @FXML ImageView imageThree;
  @FXML ImageView imageFour;
  @FXML ImageView imageFive;
  @FXML ImageView imageSix;
  @FXML ImageView imageSeven;
  @FXML ImageView imageEight;
  @FXML ImageView imageNine;
  @FXML ImageView imageTen;
  @FXML ImageView imageEleven;
  @FXML ImageView imageTwelve;
  @FXML ImageView imageThirteen;
  @FXML ImageView imageFourteen;
  @FXML ImageView imageFifteen;
  Duration duration = new Duration(500);

  // making the labels for each member and assigning them to the popover
  //  @FXML Label position = new Label("Position: Full Time Software Engineer");
  //  @FXML Label major = new Label("Major: Data Science");
  //  @FXML Label hobby = new Label("Fun Fact: Minesweeper Master");
  //  @FXML VBox vBox = new VBox(position, major, hobby);
  //  private PopOver photoSquare = new PopOver(vBox);

  @FXML Label position1 = new Label("Position: Full Time Software Engineer");
  @FXML Label major1 = new Label("Major: Data Science");
  @FXML Label hobby1 = new Label("Fun Fact: Minesweeper Master");
  @FXML VBox vBox1 = new VBox(position1, major1, hobby1);
  private PopOver photoSquare1 = new PopOver(vBox1);

  @FXML Label position2 = new Label("Position: Product Owner");
  @FXML Label major2 = new Label("Major: Computer Science");
  @FXML Label hobby2 = new Label("Fun Fact: Nintendo Fan");
  @FXML VBox vBox2 = new VBox(position2, major2, hobby2);
  private PopOver photoSquare2 = new PopOver(vBox2);

  @FXML Label position3 = new Label("Position: Assistant Lead Software Engineer");
  @FXML Label major3 = new Label("Major: Computer Science");
  @FXML Label hobby3 = new Label("Fun Fact: Middle name is \"Cowboy\"");
  @FXML VBox vBox3 = new VBox(position3, major3, hobby3);
  private PopOver photoSquare3 = new PopOver(vBox3);

  @FXML Label position4 = new Label("Position: Full Time Software Engineer");
  @FXML Label major4 = new Label("Major: Computer Science");
  @FXML Label hobby4 = new Label("Fun Fact: Plays too many instruments");
  @FXML VBox vBox4 = new VBox(position4, major4, hobby4);
  private PopOver photoSquare4 = new PopOver(vBox4);

  @FXML Label position5 = new Label("Position: Full Time Software Engineer");
  @FXML Label major5 = new Label("Major: Computer Science");
  @FXML Label hobby5 = new Label("Fun Fact: Likes to Hike");
  @FXML VBox vBox5 = new VBox(position5, major5, hobby5);
  private PopOver photoSquare5 = new PopOver(vBox5);

  @FXML
  Label position6 = new Label("Position: Documentation Analyst, Part Time Front End Developer");

  @FXML Label major6 = new Label("Major: Robotics Engineering");
  @FXML Label hobby6 = new Label("Fun Fact: I've all 9 continents");
  @FXML VBox vBox6 = new VBox(position6, major6, hobby6);
  private PopOver photoSquare6 = new PopOver(vBox6);

  @FXML Label position7 = new Label("Position: Project Manager");
  @FXML Label major7 = new Label("Major: Computer Science");
  @FXML Label hobby7 = new Label("Fun Fact: If I was an animal, I would be a monkey.\n");
  @FXML VBox vBox7 = new VBox(position7, major7, hobby7);
  private PopOver photoSquare7 = new PopOver(vBox7);

  @FXML Label position8 = new Label("Position: Scrum Master");
  @FXML Label major8 = new Label("Major: Robotics Engineering");
  @FXML Label hobby8 = new Label("Fun Fact: I race sailboats on occasion");
  @FXML VBox vBox8 = new VBox(position8, major8, hobby8);
  private PopOver photoSquare8 = new PopOver(vBox8);

  @FXML Label position9 = new Label("Position: Assistant Lead, Frontend");
  @FXML Label major9 = new Label("Major: Computer Science");
  @FXML Label hobby9 = new Label("Fun Fact: black belt in karate");
  @FXML VBox vBox9 = new VBox(position9, major9, hobby9);
  private PopOver photoSquare9 = new PopOver(vBox9);

  @FXML Label position10 = new Label("Position: Lead Developer");
  @FXML Label major10 = new Label("Major: Computer Scientist");
  @FXML Label hobby10 = new Label("Fun Fact: Part of Rowing Team");
  @FXML VBox vBox10 = new VBox(position10, major10, hobby10);
  private PopOver photoSquare10 = new PopOver(vBox10);

  //  @FXML Label position11 = new Label("Position: Part Time Marketing Coordinator");
  //  @FXML Label major11 = new Label("Major: Business Administration");
  //  @FXML Label hobby11 = new Label("Fun Fact: Can Speak French Fluently");
  //  @FXML VBox vBox11 = new VBox(position11, major11, hobby11);
  //  private PopOver photoSquare11 = new PopOver(vBox11);
  //
  //  @FXML Label position12 = new Label("Position: Full Time Project Manager");
  //  @FXML Label major12 = new Label("Major: Engineering Management");
  //  @FXML Label hobby12 = new Label("Fun Fact: Enjoys Playing Tennis");
  //  @FXML VBox vBox12 = new VBox(position12, major12, hobby12);
  //  private PopOver photoSquare12 = new PopOver(vBox12);
  //
  //  @FXML Label position13 = new Label("Position: Part Time Sales Representative");
  //  @FXML Label major13 = new Label("Major: Marketing");
  //  @FXML Label hobby13 = new Label("Fun Fact: Loves Watching Football");
  //  @FXML VBox vBox13 = new VBox(position13, major13, hobby13);
  //  private PopOver photoSquare13 = new PopOver(vBox13);
  //
  //  @FXML Label position14 = new Label("Position: Full Time Database Administrator");
  //  @FXML Label major14 = new Label("Major: Database Management");
  //  @FXML Label hobby14 = new Label("Fun Fact: Enjoys Playing Video Games");
  //  @FXML VBox vBox14 = new VBox(position14, major14, hobby14);
  //  private PopOver photoSquare14 = new PopOver(vBox14);
  //
  //  @FXML Label position15 = new Label("Position: Part Time HR Coordinator");
  //  @FXML Label major15 = new Label("Major: Human Resource Management");
  //  @FXML Label hobby15 = new Label("Fun Fact: Enjoys Reading Mystery Novels");
  //  @FXML VBox vBox15 = new VBox(position15, major15, hobby15);
  //  private PopOver photoSquare15 = new PopOver(vBox15);

  public void initialize() {

    // Cropping the images and setting sizes
    // Abby
    imageOne.setViewport(new Rectangle2D(2164, 724, 710, 710));
    imageOne.setFitHeight(150);

    // Mike
    imageTwo.setViewport(new Rectangle2D(724, 4, 710, 710));
    imageTwo.setFitHeight(150);

    // Bryce
    imageThree.setViewport(new Rectangle2D(724, 724, 710, 710));
    imageThree.setFitHeight(150);

    // Gibson
    imageFour.setViewport(new Rectangle2D(2884, 724, 710, 710));
    imageFour.setFitHeight(150);

    // Jonathan
    imageFive.setViewport(new Rectangle2D(1444, 724, 710, 710));
    imageFive.setFitHeight(150);

    // Wyatt
    imageSix.setViewport(new Rectangle2D(1444, 4, 710, 710));
    imageSix.setFitHeight(150);

    // Annie
    imageSeven.setViewport(new Rectangle2D(2164, 4, 710, 710));
    imageSeven.setFitHeight(150);

    // Theo
    imageEight.setViewport(new Rectangle2D(2884, 4, 710, 710));
    imageEight.setFitHeight(150);

    // Liv
    imageNine.setViewport(new Rectangle2D(4, 724, 710, 710));
    imageNine.setFitHeight(150);

    // Ari
    imageTen.setViewport(new Rectangle2D(4, 4, 710, 710));
    imageTen.setFitHeight(150);

    // Qui
    imageEleven.setViewport(new Rectangle2D(4, 1444, 710, 710));
    imageEleven.setFitHeight(150);

    // Wong
    imageTwelve.setViewport(new Rectangle2D(724, 1444, 710, 710));
    imageTwelve.setFitHeight(150);

    // Andrew
    imageThirteen.setViewport(new Rectangle2D(1444, 1444, 710, 710));
    imageThirteen.setFitHeight(150);

    // Hospital
    imageFourteen.setViewport(new Rectangle2D(2164, 1444, 710, 710));
    imageFourteen.setFitHeight(150);

    // CS Department
    imageFifteen.setViewport(new Rectangle2D(2884, 1444, 710, 710));
    imageFifteen.setFitHeight(150);

    // Label formatting and removing arrow
    position1.setPadding(new Insets(10, 5, 5, 10));
    major1.setPadding(new Insets(5, 5, 5, 10));
    hobby1.setPadding(new Insets(5, 5, 10, 10));
    photoSquare1.setArrowSize(0);
    photoSquare1.setFadeOutDuration(duration);

    position2.setPadding(new Insets(10, 5, 5, 10));
    major2.setPadding(new Insets(5, 5, 5, 10));
    hobby2.setPadding(new Insets(5, 5, 10, 10));
    photoSquare2.setArrowSize(0);
    photoSquare2.setFadeOutDuration(duration);

    position3.setPadding(new Insets(10, 5, 5, 10));
    major3.setPadding(new Insets(5, 5, 5, 10));
    hobby3.setPadding(new Insets(5, 5, 10, 10));
    photoSquare3.setArrowSize(0);
    photoSquare3.setFadeOutDuration(duration);

    position4.setPadding(new Insets(10, 5, 5, 10));
    major4.setPadding(new Insets(5, 5, 5, 10));
    hobby4.setPadding(new Insets(5, 5, 10, 10));
    photoSquare4.setArrowSize(0);
    photoSquare4.setFadeOutDuration(duration);

    position5.setPadding(new Insets(10, 5, 5, 10));
    major5.setPadding(new Insets(5, 5, 5, 10));
    hobby5.setPadding(new Insets(5, 5, 10, 10));
    photoSquare5.setArrowSize(0);
    photoSquare5.setFadeOutDuration(duration);

    position6.setPadding(new Insets(10, 5, 5, 10));
    major6.setPadding(new Insets(5, 5, 5, 10));
    hobby6.setPadding(new Insets(5, 5, 10, 10));
    photoSquare6.setArrowSize(0);
    photoSquare6.setFadeOutDuration(duration);

    position7.setPadding(new Insets(10, 5, 5, 10));
    major7.setPadding(new Insets(5, 5, 5, 10));
    hobby7.setPadding(new Insets(5, 5, 10, 10));
    photoSquare7.setArrowSize(0);
    photoSquare7.setFadeOutDuration(duration);

    position8.setPadding(new Insets(10, 5, 5, 10));
    major8.setPadding(new Insets(5, 5, 5, 10));
    hobby8.setPadding(new Insets(5, 5, 10, 10));
    photoSquare8.setArrowSize(0);
    photoSquare8.setFadeOutDuration(duration);

    position9.setPadding(new Insets(10, 5, 5, 10));
    major9.setPadding(new Insets(5, 5, 5, 10));
    hobby9.setPadding(new Insets(5, 5, 10, 10));
    photoSquare9.setArrowSize(0);
    photoSquare9.setFadeOutDuration(duration);

    position10.setPadding(new Insets(10, 5, 5, 10));
    major10.setPadding(new Insets(5, 5, 5, 10));
    hobby10.setPadding(new Insets(5, 5, 10, 10));
    photoSquare10.setArrowSize(0);
    photoSquare10.setFadeOutDuration(duration);

    //    position11.setPadding(new Insets(10, 5, 5, 10));
    //    major11.setPadding(new Insets(5, 5, 5, 10));
    //    hobby11.setPadding(new Insets(5, 5, 10, 10));
    //    photoSquare11.setArrowSize(0);
    //    photoSquare11.setFadeOutDuration(duration);
    //
    //    position12.setPadding(new Insets(10, 5, 5, 10));
    //    major12.setPadding(new Insets(5, 5, 5, 10));
    //    hobby12.setPadding(new Insets(5, 5, 10, 10));
    //    photoSquare12.setArrowSize(0);
    //    photoSquare12.setFadeOutDuration(duration);
    //
    //    position13.setPadding(new Insets(10, 5, 5, 10));
    //    major13.setPadding(new Insets(5, 5, 5, 10));
    //    hobby13.setPadding(new Insets(5, 5, 10, 10));
    //    photoSquare13.setArrowSize(0);
    //    photoSquare13.setFadeOutDuration(duration);
    //
    //    position14.setPadding(new Insets(10, 5, 5, 10));
    //    major14.setPadding(new Insets(5, 5, 5, 10));
    //    hobby14.setPadding(new Insets(5, 5, 10, 10));
    //    photoSquare14.setArrowSize(0);
    //    photoSquare14.setFadeOutDuration(duration);
    //
    //    position15.setPadding(new Insets(10, 5, 5, 10));
    //    major15.setPadding(new Insets(5, 5, 5, 10));
    //    hobby15.setPadding(new Insets(5, 5, 10, 10));
    //    photoSquare15.setArrowSize(0);
    //    photoSquare15.setFadeOutDuration(duration);

    // setting all of the popups for over the images
    imageOne.setOnMouseEntered(
        mouseEvent -> {
          state = 0;
          photoSquare1.show(imageOne, 10);
          while (state < 10000) {
            state++;
          }
        });
    imageOne.setOnMouseExited(
        mouseEvent -> {
          if (photoSquare1.isShowing()) {
            photoSquare1.hide();
          }
        });

    imageTwo.setOnMouseEntered(
        mouseEvent -> {
          photoSquare2.show(imageTwo);
        });
    imageTwo.setOnMouseExited(
        mouseEvent -> {
          if (photoSquare2.isShowing()) {
            photoSquare2.hide();
          }
        });

    imageThree.setOnMouseEntered(
        mouseEvent -> {
          photoSquare3.show(imageThree);
        });
    imageThree.setOnMouseExited(
        mouseEvent -> {
          if (photoSquare3.isShowing()) {
            photoSquare3.hide();
          }
        });

    //    imageThree.setOnMouseEntered(
    //        mouseEvent -> {
    //          photoSquare4.show(imageThree);
    //        });
    //    imageThree.setOnMouseExited(
    //        mouseEvent -> {
    //          if (photoSquare4.isShowing()) {
    //            photoSquare4.hide();
    //          }
    //        });

    imageFour.setOnMouseEntered(
        mouseEvent -> {
          photoSquare4.show(imageFour);
        });
    imageFour.setOnMouseExited(
        mouseEvent -> {
          if (photoSquare4.isShowing()) {
            photoSquare4.hide();
          }
        });

    imageFive.setOnMouseEntered(
        mouseEvent -> {
          photoSquare5.setTitle("");
          photoSquare5.isAutoFix();
          photoSquare5.show(imageFive);
        });
    imageFive.setOnMouseExited(
        mouseEvent -> {
          if (photoSquare5.isShowing()) {
            photoSquare5.hide();
          }
        });

    imageSix.setOnMouseEntered(
        mouseEvent -> {
          photoSquare6.show(imageSix);
        });
    imageSix.setOnMouseExited(
        mouseEvent -> {
          if (photoSquare6.isShowing()) {
            photoSquare6.hide();
          }
        });

    imageSeven.setOnMouseEntered(
        mouseEvent -> {
          photoSquare7.show(imageSeven);
        });
    imageSeven.setOnMouseExited(
        mouseEvent -> {
          if (photoSquare7.isShowing()) {
            photoSquare7.hide();
          }
        });

    imageEight.setOnMouseEntered(
        mouseEvent -> {
          photoSquare8.show(imageEight);
        });
    imageEight.setOnMouseExited(
        mouseEvent -> {
          if (photoSquare8.isShowing()) {
            photoSquare8.hide();
          }
        });

    imageNine.setOnMouseEntered(
        mouseEvent -> {
          photoSquare9.show(imageNine);
        });
    imageNine.setOnMouseExited(
        mouseEvent -> {
          if (photoSquare9.isShowing()) {
            photoSquare9.hide();
          }
        });

    imageTen.setOnMouseEntered(
        mouseEvent -> {
          photoSquare10.setArrowSize(0);
          photoSquare10.setTitle("Ari");
          // photoSquare10.setX(photoSquare10.getX() - 100);
          photoSquare10.show(imageTen);
        });
    imageTen.setOnMouseExited(
        mouseEvent -> {
          if (photoSquare10.isShowing()) {
            photoSquare10.hide();
          }
        });

    //    imageEleven.setOnMouseEntered(
    //        mouseEvent -> {
    //          photoSquare11.show(imageEleven);
    //        });
    //    imageEleven.setOnMouseExited(
    //        mouseEvent -> {
    //          if (photoSquare11.isShowing()) {
    //            photoSquare11.hide();
    //          }
    //        });
    //
    //    imageTwelve.setOnMouseEntered(
    //        mouseEvent -> {
    //          photoSquare12.show(imageTwelve);
    //        });
    //    imageTwelve.setOnMouseExited(
    //        mouseEvent -> {
    //          if (photoSquare12.isShowing()) {
    //            photoSquare12.hide();
    //          }
    //        });
    //
    //    imageThirteen.setOnMouseEntered(
    //        mouseEvent -> {
    //          photoSquare13.show(imageThirteen);
    //        });
    //    imageThirteen.setOnMouseExited(
    //        mouseEvent -> {
    //          if (photoSquare13.isShowing()) {
    //            photoSquare13.hide();
    //          }
    //        });
    //
    //    imageFourteen.setOnMouseEntered(
    //        mouseEvent -> {
    //          photoSquare14.show(imageFourteen);
    //        });
    //    imageFourteen.setOnMouseExited(
    //        mouseEvent -> {
    //          if (photoSquare14.isShowing()) {
    //            photoSquare14.hide();
    //          }
    //        });
    //
    //    imageFifteen.setOnMouseEntered(
    //        mouseEvent -> {
    //          photoSquare15.show(imageFifteen);
    //        });
    //    imageFifteen.setOnMouseExited(
    //        mouseEvent -> {
    //          if (photoSquare15.isShowing()) {
    //            photoSquare15.hide();
    //          }
    //        });
  }

  /*
   public AboutController() {
     imageOne.setOnMouseEntered(
         mouseEvent -> {
           position = new Label("Position: Full Time Software Engineer");
           major = new Label("Major: Computer Science");
           hobby = new Label("Fun Fact: Badminton Master");
           popOver.show(imageOne, -3);
         });
     imageOne.setOnMouseExited(
         mouseEvent -> {
           if (popOver.isShowing()) {
             popOver.hide();
           }
         });

     imageTwo.setOnMouseEntered(
         mouseEvent -> {
           popOver.show(imageTwo, -3);
         });
     imageTwo.setOnMouseExited(
         mouseEvent -> {
           if (popOver.isShowing()) {
             popOver.hide();
           }
         });

     imageThree.setOnMouseEntered(
         mouseEvent -> {
           popOver.show(imageThree, -3);
         });
     imageThree.setOnMouseExited(
         mouseEvent -> {
           if (popOver.isShowing()) {
             popOver.hide();
           }
         });

     imageThree.setOnMouseEntered(
         mouseEvent -> {
           popOver.show(imageThree, -3);
         });
     imageThree.setOnMouseExited(
         mouseEvent -> {
           if (popOver.isShowing()) {
             popOver.hide();
           }
         });

     imageFour.setOnMouseEntered(
         mouseEvent -> {
           popOver.show(imageFour, -3);
         });
     imageFour.setOnMouseExited(
         mouseEvent -> {
           if (popOver.isShowing()) {
             popOver.hide();
           }
         });

     imageFive.setOnMouseEntered(
         mouseEvent -> {
           popOver.show(imageFive, -3);
         });
     imageFive.setOnMouseExited(
         mouseEvent -> {
           if (popOver.isShowing()) {
             popOver.hide();
           }
         });

     imageSix.setOnMouseEntered(
         mouseEvent -> {
           popOver.show(imageSix, -3);
         });
     imageSix.setOnMouseExited(
         mouseEvent -> {
           if (popOver.isShowing()) {
             popOver.hide();
           }
         });

     imageSeven.setOnMouseEntered(
         mouseEvent -> {
           popOver.show(imageSeven, -3);
         });
     imageSeven.setOnMouseExited(
         mouseEvent -> {
           if (popOver.isShowing()) {
             popOver.hide();
           }
         });

     imageEight.setOnMouseEntered(
         mouseEvent -> {
           popOver.show(imageEight, -3);
         });
     imageEight.setOnMouseExited(
         mouseEvent -> {
           if (popOver.isShowing()) {
             popOver.hide();
           }
         });

     imageNine.setOnMouseEntered(
         mouseEvent -> {
           popOver.show(imageNine, -3);
         });
     imageNine.setOnMouseExited(
         mouseEvent -> {
           if (popOver.isShowing()) {
             popOver.hide();
           }
         });

     imageTen.setOnMouseEntered(
         mouseEvent -> {
           popOver.show(imageTen, -3);
         });
     imageTen.setOnMouseExited(
         mouseEvent -> {
           if (popOver.isShowing()) {
             popOver.hide();
           }
         });

     imageEleven.setOnMouseEntered(
         mouseEvent -> {
           popOver.show(imageEleven, -3);
         });
     imageEleven.setOnMouseExited(
         mouseEvent -> {
           if (popOver.isShowing()) {
             popOver.hide();
           }
         });

     imageTwelve.setOnMouseEntered(
         mouseEvent -> {
           popOver.show(imageTwelve, -3);
         });
     imageTwelve.setOnMouseExited(
         mouseEvent -> {
           if (popOver.isShowing()) {
             popOver.hide();
           }
         });

     imageThirteen.setOnMouseEntered(
         mouseEvent -> {
           popOver.show(imageThirteen, -3);
         });
     imageThirteen.setOnMouseExited(
         mouseEvent -> {
           if (popOver.isShowing()) {
             popOver.hide();
           }
         });

     imageFourteen.setOnMouseEntered(
         mouseEvent -> {
           popOver.show(imageFourteen, -3);
         });
     imageFourteen.setOnMouseExited(
         mouseEvent -> {
           if (popOver.isShowing()) {
             popOver.hide();
           }
         });

     imageFifteen.setOnMouseEntered(
         mouseEvent -> {
           popOver.show(imageFifteen, -3);
         });
     imageFifteen.setOnMouseExited(
         mouseEvent -> {
           if (popOver.isShowing()) {
             popOver.hide();
           }
         });
   }

  */
}
