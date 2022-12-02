package com.example.racetracker;

import com.example.racetracker.domain.Driver;
import com.example.racetracker.manager.TrackerManager;
import com.example.racetracker.repository.DriverRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.channel.AbstractSubscribableChannel;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@SpringBootApplication
@Controller
public class RaceTrackerApplication {
	private final TrackerManager trackerManager;
	private final AbstractSubscribableChannel laptimeChannel;
	private final AbstractSubscribableChannel betChannel;
	private final DriverRepository  driverRepo;

	public RaceTrackerApplication(TrackerManager trackerManager, DriverRepository driverRepo) {
		this.trackerManager = trackerManager;
		this.laptimeChannel = (DirectChannel) TrackerManager.getContext().getBean("laptimeChannel");
		this.betChannel = (DirectChannel) TrackerManager.getContext().getBean("betApiChannel");
		this.driverRepo = driverRepo;

		initializeDrivers();
		driverRepo.findAll().stream().forEach(element -> System.out.println(element.getName()));
	}

	public static void main(String[] args) {
		SpringApplication.run(RaceTrackerApplication.class, args);
	}

	@GetMapping("/")
	public String getHomepage(Model model) {
		model.addAttribute("leaderboard", trackerManager.getLeaderboard());
		model.addAttribute("lapCount", trackerManager.getLapCount());
		model.addAttribute("fastestLap", trackerManager.getFastestLap());
		model.addAttribute("betTotals", trackerManager.getBetTotals());
		return "tracker";
	}

	@PostMapping("/")
	@ResponseBody
	public void updateDriver(@RequestBody String data) {
		this.laptimeChannel.send(MessageBuilder.withPayload(data).build());
	}

	@PostMapping("/bet")
	@ResponseBody
	public void placeBet(@RequestBody String data) {
		// new Thread(new ForwardBetRunnable(data)).start();
		this.betChannel.send(MessageBuilder.withPayload(data).build());
	}

	private void initializeDrivers() {
		driverRepo.save(new Driver("VER", 0.00, 0.00));
		driverRepo.save(new Driver("PER", 0.00, 0.00));
		driverRepo.save(new Driver("LEC", 0.00, 0.00));
		driverRepo.save(new Driver("SAI", 0.00, 0.00));
		driverRepo.save(new Driver("HAM", 0.00, 0.00));
		driverRepo.save(new Driver("RUS", 0.00, 0.00));
		driverRepo.save(new Driver("OCO", 0.00, 0.00));
		driverRepo.save(new Driver("ALO", 0.00, 0.00));
		driverRepo.save(new Driver("NOR", 0.00, 0.00));
		driverRepo.save(new Driver("RIC", 0.00, 0.00));
		driverRepo.save(new Driver("MSC", 0.00, 0.00));
		driverRepo.save(new Driver("MAG", 0.00, 0.00));
		driverRepo.save(new Driver("BOT", 0.00, 0.00));
		driverRepo.save(new Driver("ZHO", 0.00, 0.00));
		driverRepo.save(new Driver("STR", 0.00, 0.00));
		driverRepo.save(new Driver("VET", 0.00, 0.00));
		driverRepo.save(new Driver("LAT", 0.00, 0.00));
		driverRepo.save(new Driver("ALB", 0.00, 0.00));
		driverRepo.save(new Driver("GAS", 0.00, 0.00));
		driverRepo.save(new Driver("TSU", 0.00, 0.00));
	}
}
