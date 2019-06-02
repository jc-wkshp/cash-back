package com.innov.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.logging.Logger;

import com.uber.jaeger.Configuration;
import com.uber.jaeger.samplers.ConstSampler;    

import io.opentracing.Tracer;

@SpringBootApplication
public class CashBackApplication {

	private static final Logger logger = Logger.getLogger(CashBackApplication.class.getName());
	
	@Bean
	public Tracer jaegerTracer() {
		return new Configuration("cash-back", new Configuration.SamplerConfiguration(ConstSampler.TYPE, 1),
				new Configuration.ReporterConfiguration())
				.getTracer();
	}

	
	public static void main(String[] args) {
		SpringApplication.run(CashBackApplication.class, args);
	}
}