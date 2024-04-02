package es.codeurj.mortez365.controller;


import es.codeurj.mortez365.DTO.WalletDataDTO;
import es.codeurj.mortez365.model.Wallet;
import es.codeurj.mortez365.service.WalletService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/wallets")
public class WalletRestController {

    @Autowired
    private WalletService walletService;


    @Operation(summary = "Get All Wallets", description = "Retrieve all wallets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallets successfully retrieved", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = WalletDataDTO.class)))),
            @ApiResponse(responseCode = "404", description = "No wallets found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/")
    public ResponseEntity<Iterable<WalletDataDTO>> getWallets() {
        List<Wallet> wallets =  walletService.findAll();
        if (wallets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Collection<WalletDataDTO> walletList = new ArrayList<>();
        for (Wallet wallet : wallets) {
            WalletDataDTO walletDto = new WalletDataDTO(wallet.getOwner(), wallet.getMoney(), wallet.getCard_number());
            walletList.add(walletDto);
        }
        return new ResponseEntity<>(walletList, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<WalletDataDTO> getWallet(@PathVariable Long id){
        Optional<Wallet> wallet = walletService.findById(id);
        if (wallet.isPresent()) {
            WalletDataDTO walletDTO = new WalletDataDTO(wallet.get().getOwner(), wallet.get().getMoney(), wallet.get().getCard_number());
            return ResponseEntity.ok(walletDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //No delete method because no sense that is posible to delete a wallet same happends with post method

    @Operation(summary = "Get Wallet by ID", description = "Retrieve a wallet by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallet successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WalletDataDTO.class))),
            @ApiResponse(responseCode = "404", description = "Wallet not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Wallet> replaceWallet(@RequestBody Wallet newWallet, @PathVariable Long id) {
        Optional<Wallet> wallet = walletService.findById(id);

        if(wallet.isPresent()){
            newWallet.setId(id);
            walletService.save(newWallet);
            URI location = fromCurrentRequest().path("/{id}").buildAndExpand(newWallet.getId()).toUri();
            return ResponseEntity.ok().location(location).body(newWallet);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
