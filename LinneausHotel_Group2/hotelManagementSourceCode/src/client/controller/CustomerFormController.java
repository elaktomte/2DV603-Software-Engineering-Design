package client.controller;

import client.model.customer.Address;
import client.model.customer.CreditCard;
import client.model.customer.RealCustomer;
import client.controller.ScreenController.Screen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
/**
 * 
 * This is the client.controller for the form where we enter the customers information.
 *
 */
public class CustomerFormController implements Initializable
{
    private FacadeController facadeController;
    
    @FXML
    private TextField first_name_field;
    
    @FXML
    private TextField last_name_field;
    
    @FXML
    private TextField telephone_number_field;
    
    @FXML
    private TextField personal_number_field;
    
    @FXML
    private ComboBox<RealCustomer.PowerLevel> powerLevel;
    
    @FXML
    private TextField address_field;
    
    @FXML
    private TextField postal_code_field;
    
    @FXML
    private TextField city_field;
    
    @FXML
    private TextField card_number_field;
    
    @FXML
    private ComboBox<String> expiration_month;
    
    @FXML
    private ComboBox<String> expiration_year;
    
    @FXML
    private TextField cvc_code_field;
    
    @FXML
    private Button cancel_button;
    
    @FXML
    private Button confirm_button;
    
    @FXML
    private TextField country_field;
    
    @FXML
    private TextField passport_number_field;
    
    @FXML
    public void cancelButton() throws IOException {
        facadeController.changeScreen(Screen.MAIN);
    }
    @FXML
    public void confirmButton() throws IOException {
    
        RealCustomer customer = new RealCustomer();
        try
        {
            if(first_name_field.getText().equals(""))
            {
                throw new IllegalArgumentException("There was no first name");
            } else
            {
                customer.setFirstName(first_name_field.getText());
            }
            if(last_name_field.getText().equals(""))
            {
                throw new IllegalArgumentException("There was no last name");
            } else
            {
                customer.setLastName(last_name_field.getText());
            }
    
            Address address = new Address();
    
            if(address_field.getText().equals(""))
            {
                throw new IllegalArgumentException("There was no address");
            } else
            {
                address.setStreetName(address_field.getText());
            }
            if(city_field.getText().equals(""))
            {
                throw new IllegalArgumentException("There was no city");
            } else
            {
                address.setCity(city_field.getText());
            }
    
            try
            {
                address.setPostalCode(Integer.parseInt(postal_code_field.getText()));
            } catch(Exception e)
            {
                throw new IllegalArgumentException("The postal code must be digits only");
            }
            if(country_field.getText().equals(""))
            {
                throw new IllegalArgumentException("There was no country");
            } else
            {
                address.setCountry(country_field.getText());
            }
            customer.setAddress(address);
    
            if(passport_number_field.getText().equals(""))
            {
                throw new IllegalArgumentException("There was no passport number");
            } else
            {
                customer.setPassportNumber(passport_number_field.getText());
            }
    
            customer.setPowerLevel(powerLevel.getValue());       // TODO, should this be automated???
    
            if(telephone_number_field.getText().equals(""))
            {
                throw new IllegalArgumentException("There was no telephone number");
            } else
            {
                customer.setTelephoneNumber(telephone_number_field.getText());
            }
            if(personal_number_field.getText().equals(""))
            {
                throw new IllegalArgumentException("There was no personal number");
            } else
            {
                customer.setPersonalNumber(personal_number_field.getText());
            }
            CreditCard card = new CreditCard();
            if(card_number_field.getText().equals(""))
            {
                throw new IllegalArgumentException("There was no card number");
            } else
            {
                card.setCardNumber(card_number_field.getText());
            }
            if(expiration_month.getValue() == null)
            {
                throw new IllegalArgumentException("There was no expiration month");
            }
            else
            {
                card.setExpMonth(Integer.parseInt(expiration_month.getValue()));
            }
            if(expiration_year.getValue() == null)
            {
                throw new IllegalArgumentException("There was no expiration year");
            }
            else
            {
                card.setExpYear(Integer.parseInt(expiration_year.getValue()));
            }
            try
            {
                card.setCvc(Integer.parseInt(cvc_code_field.getText()));
            }
            catch(Exception e)
            {
                throw new IllegalArgumentException("The cvc code must be in integers only");
            }
            customer.setCreditCard(card);
            facadeController.realizeBooking(customer);
            
            facadeController.updateBookingInProgress(customer);
            
            facadeController.changeScreen(Screen.CONFIRM_BOOKING);
        }
        catch(IllegalArgumentException e)
        {
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("All fields must be filled in to continue");
            alert.setContentText(e.getMessage());
            alert.show();
        }
        
        
    }
    
    public void setFacadeController(FacadeController facadeController) {
        this.facadeController = facadeController;
    }
    public boolean hasNoCentralController() {
        return facadeController == null;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        expiration_month.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
    
        int currentYear = LocalDate.now().getYear();
        for(int i = currentYear; i < currentYear + 6; i++)
        {
            expiration_year.getItems().add(("" + i).substring(2));  // Take last two digits of current year and five consecutive years
        }
        
        powerLevel.getItems().addAll(RealCustomer.PowerLevel.values());
        powerLevel.setValue(RealCustomer.PowerLevel.NONE);
    }
}

