package fr.intellcap.backend.service;

import fr.intellcap.backend.dto.PartnerRequestDTO;
import fr.intellcap.backend.entity.PartnerRequest;
import fr.intellcap.backend.repository.PartnerRequestRepository;
import org.springframework.stereotype.Service;

@Service
public class PartnerRequestService {

    private final PartnerRequestRepository partnerRequestRepository;

    public PartnerRequestService(PartnerRequestRepository partnerRequestRepository) {
        this.partnerRequestRepository = partnerRequestRepository;
    }

    public PartnerRequest createFromDTO(PartnerRequestDTO dto) {
        PartnerRequest request = new PartnerRequest();
        request.setFullName(dto.getFullName());
        request.setOrganization(dto.getOrganization());
        request.setCountry(dto.getCountry());
        request.setCity(dto.getCity());
        request.setEmail(dto.getEmail());
        request.setWhatsapp(dto.getWhatsapp());
        request.setNeedExpression(dto.getNeedExpression());
        request.setLinkedin(dto.getLinkedin());
        request.setSurfaceAvailable(dto.getSurfaceAvailable());
        request.setPreciseLocation(dto.getPreciseLocation());
        request.setHumanResources(dto.getHumanResources());
        request.setEstimatedBudget(dto.getEstimatedBudget());
        return partnerRequestRepository.save(request);
    }
}