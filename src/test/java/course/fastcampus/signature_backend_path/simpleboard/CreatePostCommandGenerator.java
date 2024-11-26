package course.fastcampus.signature_backend_path.simpleboard;

import autoparams.ObjectQuery;
import autoparams.ResolutionContext;
import autoparams.generator.ObjectGeneratorBase;
import course.fastcampus.signature_backend_path.simpleboard.post.model.CreatePostCommand;
import org.instancio.Instancio;

import java.util.concurrent.ThreadLocalRandom;

import static org.instancio.Select.field;

public class CreatePostCommandGenerator extends ObjectGeneratorBase<CreatePostCommand> {

    @Override
    protected CreatePostCommand generateObject(ObjectQuery query, ResolutionContext context) {

        String title = context.resolve(String.class);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int passwordLength = random.nextInt(4, 21);

        String password = context.resolve(String.class)
                .substring(0, passwordLength);

        return Instancio.of(CreatePostCommand.class)
                .ignore(field(CreatePostCommand::boardId))
                .generate(field(CreatePostCommand::username), gen ->
                        gen.string()
                                .minLength(3)
                                .maxLength(20)
                                .alphaNumeric()
                                .lowerCase())
                .set(field(CreatePostCommand::password), password)
                .generate(field(CreatePostCommand::email), gen -> gen.net().email())
                .set(field(CreatePostCommand::title), title)
                .create();
    }
}
