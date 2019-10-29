package org.san.home.clients.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.san.home.clients.dto.ClientDto;
import org.san.home.clients.model.Client;
import org.san.home.clients.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/clients")
@Slf4j
@Api("Simple API for managing clients information")
public class ClientController {
    @Autowired
    private ClientService clientService;


    @Bean
    private ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @ApiOperation(value = "View a list of clients", response = Iterable.class)
    //@WrapException(errorCode = GET_ALL_FAILED)
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Collection<Resource<ClientDto>> findAll() {
        Collection<Resource<ClientDto>> clients =
                clientService.findAll().stream()
                        .map(client -> new Resource<>(modelMapper().map(client, ClientDto.class)))
                        .collect(Collectors.toList());
        clients.stream().forEach(
                client -> client.add(linkTo(methodOn(ClientController.class).get(client.getContent().getId())).withSelfRel()));
        return clients;
    }

    @ApiOperation(value = "Get clients by clients id", response = Resource.class)
    //@WrapException(errorCode = GET_client_FAILED)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public Resource<ClientDto> get(@ApiParam(value = "clients id") @PathVariable("id") Long id) {
        Resource<ClientDto> accRes = new Resource<>(modelMapper().map(
                clientService.get(id), ClientDto.class));
        accRes.add(linkTo(methodOn(ClientController.class).findAll()).withRel("list"));
        return accRes;
    }

    @ApiOperation(value = "Add clients", response = ClientDto.class)
    //@WrapException(errorCode = ADD_client_FAILED)
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto add(@Valid @RequestBody ClientDto clientDto){
        clientDto.setId(null);
        return modelMapper().map(
                clientService.add(modelMapper().map(clientDto, Client.class)),
                ClientDto.class);
    }

    @ApiOperation(value = "Update clients", response = ClientDto.class)
    //@WrapException(errorCode = UPDATE_client_FAILED)
    @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ClientDto updateClient(@Valid @RequestBody ClientDto clientDto){
        return modelMapper().map(
                clientService.update(modelMapper().map(clientDto, Client.class)),
                ClientDto.class);
    }

}
