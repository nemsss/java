package com.teamapt.transfers;

import com.teamapt.transfers.Entities.TrxType;
import com.teamapt.transfers.Repos.TrxsTimeRepository;
import org.hibernate.Hibernate;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamapt.transfers.Entities.User;
import com.teamapt.transfers.Entities.Trxs;
import com.teamapt.transfers.Repos.UserRepository;
import com.teamapt.transfers.Repos.TrxsRepository;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping(path="/transfers")
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TrxsRepository trxsRepo;
    @Autowired
    private TrxsTimeRepository trxsTimeRepo;

    @GetMapping(path="/add")
    public @ResponseBody String addNewUser (@RequestParam String name
            , @RequestParam String email) {

        User n = new User();
        n.setName(name);
        n.setEmail(email);
        n.setBalance(450000.00);
        userRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/allusers")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

    @GetMapping(path="/alltrxs")
    public @ResponseBody Iterable<Trxs> getAllTrxs() {
        // This returns a JSON or XML with the users
        Hibernate.initialize(Trxs.class);
        return trxsRepo.findAll();
    }

    @GetMapping(path="/transfer")
    public @ResponseBody String makeTransfer (@RequestParam String from
            , @RequestParam String amount, @RequestParam String to) {

        Double amt = Double.valueOf(amount);
        User sender = null;
        User recipient = null;

        try{
             sender = userRepository.findById(Integer.valueOf(from)).get();
             recipient = userRepository.findById(Integer.valueOf(to)).get();

        } catch (Exception e){
            e.printStackTrace();
            return "Failed";
        }

        if(sender == null || recipient == null){
            return "Failed";
        }

        

        //Check for duplicate transactions todo - a way to limit the query results.
        Iterable<Trxs> posibles = trxsRepo.findTrxsByUser(sender);
        Trxs mostRecent = ((List<Trxs>) posibles).get(0);

        //// TODO: 05/08/2018  Also add check for TimeStamps in close ranges.
        if(mostRecent.getAmount()==amt){
            return "This is just in case you almost did the same transfer twice. Please try again after a while " +
                    "if this action was deliberate. Thank you.";
        }


        if(sender.getBalance() > amt){
            sender.setBalance(
                    sender.getBalance() - amt
            );

            recipient.setBalance(
                    recipient.getBalance() + amt
            );

            if(createTrxn(sender, recipient, amt)){
                userRepository.save(sender);
                userRepository.save(recipient);
            }


        } else {
            return "Insufficient funds";
        }

        return "Success";
    }

    public @ResponseBody boolean createTrxn (User sender
            , User recipient, double amount) {

        Trxs debitTrx = new Trxs();
        debitTrx.setAmount(amount);
        debitTrx.setTrxType(TrxType.DEBIT);
        debitTrx.setUser(sender);

        Trxs creditTrx = new Trxs();
        creditTrx.setAmount(amount);
        creditTrx.setTrxType(TrxType.CREDIT);
        creditTrx.setUser(recipient);

        try {
            trxsRepo.save(debitTrx);
            trxsRepo.save(creditTrx);
        } catch (Exception e){
            System.out.println(e.toString());
            return false;
        }

        return true;
    }
}
