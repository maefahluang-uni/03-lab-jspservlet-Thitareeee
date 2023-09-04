package th.mfu;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/calbmi")
public class BMICalculatorServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get parameter from request: "weight" and "height"
        String weightParam = request.getParameter("weight");
        String heightParam = request.getParameter("height");

        try {
            // Convert weight and height parameters to double (you may need to handle
            // exceptions)
            double weight = Double.parseDouble(weightParam);
            double height = Double.parseDouble(heightParam);

            // Calculate BMI
            double bmi = calculateBMI(weight, height);

            // Determine the build from BMI
            String build = determineBuild(bmi);

            // Add BMI and build to the request's attribute
            request.setAttribute("bmi", bmi);
            request.setAttribute("build", build);

            // Forward to JSP
            request.getRequestDispatcher("/bmi_result.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            // Handle invalid input (e.g., non-numeric weight or height)
            response.getWriter().println("Invalid input. Please enter numeric values for weight and height.");
        }
    }

    // Calculate BMI using the given weight (in kilograms) and height (in meters)
    private double calculateBMI(double weight, double height) {
        return weight / (height * height);
    }

    // Determine the build from BMI (you can define your own logic for this)
    private String determineBuild(double bmi) {
        if (bmi < 18.5) {
            return "underweight";
        } else if (bmi >= 18.5 && bmi < 24.9) {
            return "normal";
        } else if (bmi >= 25 && bmi < 29.9) {
            return "overweight";
        } else {
            return "extermely obese";
        }
    }
}