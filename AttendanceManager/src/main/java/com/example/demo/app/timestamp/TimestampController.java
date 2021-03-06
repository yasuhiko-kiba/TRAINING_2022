package com.example.demo.app.timestamp;

import java.util.Locale;

import javax.swing.JOptionPane;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Timestamp;
import com.example.demo.service.TimestampService;

@Controller
@RequestMapping("/AttendanceManagement")
public class TimestampController {
	@Autowired
    MessageSource messagesource;
	
	private final TimestampService timestampService;
	
	public TimestampController(TimestampService timestampService) {
		this.timestampService = timestampService;
	}

	@GetMapping("/timestamp")
	public String timestamp(Model model) {
		model.addAttribute("title", "timestamp");
		boolean attendance_status = timestampService.getAttendance_status("test");
		model.addAttribute("attendance_status", attendance_status);

		return "timestamp/timestamp";
	}
	
	@PostMapping("/timestamp")
	public String timestamp(@Valid @ModelAttribute TimestampForm timestampForm,
	        BindingResult result,
	        Model model) {
		Timestamp timestamp = new Timestamp();
		timestamp.setUser_id(timestampForm.getUser_id());
		timestamp.setName(timestampForm.getName());
		timestamp.setAttendance_status(timestampForm.isAttendance_status());

        if (!result.hasErrors()) {
        	//TimestampFormのデータをtimestampsに格納
        	timestampService.Insert(timestamp);
        }else {
    		// メッセージ表示
    		String message = messagesource.getMessage("E0002", new String[]{"打刻処理"}, Locale.JAPAN);
    		JOptionPane.showMessageDialog(null, message);
        }
        return "redirect:/AttendanceManagement/timestamp";
	}
	

	
}