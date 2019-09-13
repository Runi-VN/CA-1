/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandpattern;

import facades.WhoDidWhatFacade;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Malte
 */
public class MakeWorkCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, WhoDidWhatFacade facade) {

        // Input from HTML form
        String name = request.getParameter("name");
        System.out.println("NAME: " + name);
        String work = request.getParameter("work");
        System.out.println("DATE: " + work);
        
        facade.makeWork(name, work);

        return "groupContract";
    }

}
