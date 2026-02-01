package jsp.springboot;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("shipment/details")
public class ShipmentController {
	@Autowired
	private ShipmentRepository shipmentRespository;
	
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Shipment>> saveShipment(@RequestBody Shipment shipment)
	{
		Shipment s = shipmentRespository.save(shipment);
		ResponseStructure<Shipment> response = new ResponseStructure<Shipment>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Succesfull");
		response.setData(s);
		return new ResponseEntity<ResponseStructure<Shipment>>(response,HttpStatus.CREATED);
		
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Shipment>>> allshipmentDetails()
	{
		List<Shipment> shipments = shipmentRespository.findAll();
		ResponseStructure<List<Shipment>> response = new ResponseStructure<List<Shipment>>();
		if(shipments.isEmpty())
		{
			throw  new NoRecordAvilableException("No records avilable in db");
			
		}
		else {
			response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Fetched Succesfully");
		response.setData(shipments);
		return new ResponseEntity<ResponseStructure<List<Shipment>>>(response,HttpStatus.OK);
		
		}
	}
		@GetMapping("/{id}")
		public ResponseEntity<ResponseStructure<Shipment>> shipmentDetail(@PathVariable Integer  id)
		{
			Optional<Shipment> opt = shipmentRespository.findById(id);
			ResponseStructure<Shipment> response = new ResponseStructure<Shipment>();
			if(opt.isPresent())
			{
				response.setStatusCode(HttpStatus.OK.value());
				response.setMessage("record fetched ");
				response.setData(opt.get());
				return new ResponseEntity<ResponseStructure<Shipment>>(response,HttpStatus.OK);
						
			}
			else
				throw new IdNotFoundException("id is not present in the database");
			
			
		}
		
		@PutMapping("/{id}")
		public ResponseEntity<ResponseStructure<Shipment>> updatingshipment(@RequestBody Shipment shipment )
		{
			ResponseStructure<Shipment> response = new ResponseStructure<Shipment>();
			if(shipment.getId()==null)
			{
				throw new IdNotFoundException("id must be provided");
				
			}
			Optional<Shipment> opt = shipmentRespository.findById(shipment.getId());
			if(opt.isPresent())
			{
				response.setStatusCode(HttpStatus.OK.value());
				response.setMessage("record updated ");
				response.setData(opt.get());
				return new ResponseEntity<ResponseStructure<Shipment>>(response,HttpStatus.OK);
						
			}
			else
				throw new IdNotFoundException("id is not present in the database");
			
			


		}
		@DeleteMapping("/{id}")
		public ResponseEntity<ResponseStructure<String>> deletingShipment(@PathVariable Integer id){
			ResponseStructure<String> response = new ResponseStructure();
			Optional<Shipment> opt = shipmentRespository.findById(id);
			
			if(opt.isPresent())
			{
				response.setStatusCode(HttpStatus.OK.value());
				response.setMessage(" deleted ");
				response.setData("success");
				return new ResponseEntity<ResponseStructure<String>>(response,HttpStatus.OK);
						
			}
			else 
				throw new IdNotFoundException("id not present in Db");
			

		}
	
		

	}
	

//{
//	  
//	  "trackingNumber": "TRK1001",
//	  "origin": "Bangalore",
//	  "destination": "Chennai",
//	  "deliveryDate": "2026-02-06",
//	  "carrier": "BlueDart",
//	  "status": "DELIVERED"
//	}
//{
//	  
//	  "trackingNumber": "TRK1005",
//	  "origin": "Jaipur",
//	  "destination": "Udaipur",
//	  "deliveryDate": "2026-02-07",
//	  "carrier": "Ekart",
//	  "status": "DISPATCHED"
//	}
//{"carrier": "BlueDart",
//"deliveryDate": "2026-02-05",
//"destination": "Hyderabad",
//"id": 1,
//"origin": "Bangalore",
//"status": null,
//"trackingNumber": "TRK123456789"
//}


