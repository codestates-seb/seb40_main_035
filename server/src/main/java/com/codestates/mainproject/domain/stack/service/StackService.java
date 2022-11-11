package com.codestates.mainproject.domain.stack.service;

import com.codestates.mainproject.domain.stack.entity.Stack;
import com.codestates.mainproject.domain.stack.repository.StackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class StackService {
    private final StackRepository stackRepository;

    public Stack createStack(Stack stack) {
        verifyExistingName(stack.getName());
        return stackRepository.save(stack);
    }

    public Stack findStack(long stackId) {
        return findVerifiedStack(stackId);
    }

    public List<Stack> findStacks() {
        return stackRepository.findAll();
    }

    public void deleteStack(long stackId) {
        Stack stack = findVerifiedStack(stackId);
        stackRepository.delete(stack);
    }

    public Stack findVerifiedStack(long stackId) {
        Optional<Stack> optionalStack = stackRepository.findById(stackId);
        return optionalStack.orElseThrow(() -> new RuntimeException("존재하지 않는 스택입니다."));
    }

    private void verifyExistingName(String name) {
        Optional<Stack> optionalStack = stackRepository.findByName(name);

        if (optionalStack.isPresent()) {
            throw new RuntimeException("이미 존재하는 이름입니다.");
        }
    }
}
